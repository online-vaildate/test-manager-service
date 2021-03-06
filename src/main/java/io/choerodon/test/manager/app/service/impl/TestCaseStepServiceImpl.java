package io.choerodon.test.manager.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.test.manager.api.dto.TestCaseStepDTO;
import io.choerodon.test.manager.api.dto.TestCycleCaseStepDTO;
import io.choerodon.test.manager.app.service.TestCaseStepService;
import io.choerodon.test.manager.app.service.TestCycleCaseStepService;
import io.choerodon.test.manager.domain.test.manager.entity.TestCaseStepE;
import io.choerodon.test.manager.domain.service.ITestCaseStepService;
import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseE;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseStepE;
import io.choerodon.test.manager.domain.test.manager.entity.TestStatusE;
import io.choerodon.test.manager.domain.test.manager.factory.TestCaseStepEFactory;
import io.choerodon.test.manager.domain.test.manager.factory.TestCycleCaseEFactory;
import io.choerodon.test.manager.domain.test.manager.factory.TestCycleCaseStepEFactory;
import io.choerodon.test.manager.domain.test.manager.factory.TestStatusEFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
public class TestCaseStepServiceImpl implements TestCaseStepService {
	@Autowired
	ITestCaseStepService iTestCaseStepService;



	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeStep(TestCaseStepDTO testCaseStepDTO) {
		iTestCaseStepService.removeStep(ConvertHelper.convert(testCaseStepDTO, TestCaseStepE.class));
	}


	@Override
	public List<TestCaseStepDTO> query(TestCaseStepDTO testCaseStepDTO) {
		return ConvertHelper.convertList(iTestCaseStepService.query(ConvertHelper.convert(testCaseStepDTO, TestCaseStepE.class)), TestCaseStepDTO.class);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TestCaseStepDTO changeStep(TestCaseStepDTO testCaseStepDTO, Long projectId) {
		TestCaseStepE testCaseStepE = ConvertHelper.convert(testCaseStepDTO, TestCaseStepE.class);
		if (testCaseStepE.getStepId() == null) {
			testCaseStepE = testCaseStepE.createOneStep();
			runCycleCaseStep(testCaseStepE, projectId);
		} else {
			testCaseStepE = testCaseStepE.changeOneStep();
		}
		return ConvertHelper.convert(testCaseStepE, TestCaseStepDTO.class);
	}

	private void runCycleCaseStep(TestCaseStepE testCaseStepE, Long projectId) {
		TestCycleCaseE testCycleCaseE = TestCycleCaseEFactory.create();
		testCycleCaseE.setIssueId(testCaseStepE.getIssueId());
		List<TestCycleCaseE> testCaseStepES = testCycleCaseE.querySelf();
		TestCycleCaseStepE testCycleCaseStepE = new TestCycleCaseStepEFactory().create();
		Long status = TestStatusEFactory.create().getDefaultStatusId(projectId, TestStatusE.STATUS_TYPE_CASE_STEP);
		testCaseStepES.forEach(v -> {
			testCycleCaseStepE.setExecuteId(v.getExecuteId());
			testCycleCaseStepE.setStepId(testCaseStepE.getStepId());
			testCycleCaseStepE.setStepStatus(status);
			testCycleCaseStepE.addSelf();
		});
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<TestCaseStepDTO> batchInsertStep(List<TestCaseStepDTO> testCaseStepDTO, Long projectId) {
		List<TestCaseStepDTO> result = new ArrayList<>();
		String[] rank = new String[1];
		testCaseStepDTO.forEach(v -> {
			v.setLastRank(rank[0]);
			TestCaseStepDTO temp = changeStep(v, projectId);
			rank[0] = temp.getRank();
			result.add(temp);
		});
		return result;
	}

	@Transactional
	@Override
	public TestCaseStepDTO clone(TestCaseStepDTO testCaseStepDTO, Long projectId) {
		TestCaseStepE testCaseStepE = ConvertHelper.convert(testCaseStepDTO, TestCaseStepE.class);
		List<TestCaseStepE> steps = testCaseStepE.querySelf();
		if (steps.size() != 1) {
			throw new CommonException("error.clone.case.step");
		}
		testCaseStepE = steps.get(0);
		testCaseStepE.setStepId(null);
		testCaseStepE.setLastRank(testCaseStepE.getLastedStepRank());
		testCaseStepE.setObjectVersionNumber(null);
		return changeStep(ConvertHelper.convert(testCaseStepE, TestCaseStepDTO.class), projectId);

	}

}
