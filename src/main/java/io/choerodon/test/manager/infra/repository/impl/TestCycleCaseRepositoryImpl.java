package io.choerodon.test.manager.infra.repository.impl;

import io.choerodon.test.manager.api.dto.TestCycleCaseDTO;
import io.choerodon.test.manager.domain.test.manager.entity.TestCycleCaseE;
import io.choerodon.test.manager.domain.repository.TestCycleCaseRepository;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseAttachmentRelDO;
import io.choerodon.test.manager.infra.dataobject.TestCycleCaseDO;
import io.choerodon.test.manager.infra.mapper.TestCycleCaseMapper;
import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
public class TestCycleCaseRepositoryImpl implements TestCycleCaseRepository {
    @Autowired
    TestCycleCaseMapper testCycleCaseMapper;

    @Override
    public TestCycleCaseE insert(TestCycleCaseE testCycleCaseE) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
        if (testCycleCaseMapper.insert(convert) != 1) {
            throw new CommonException("error.testStepCase.insert");
        }
        return ConvertHelper.convert(convert, TestCycleCaseE.class);
    }

    @Override
    public void delete(TestCycleCaseE testCycleCaseE) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
        testCycleCaseMapper.delete(convert);
    }

    @Override
    public TestCycleCaseE update(TestCycleCaseE testCycleCaseE) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
        if (testCycleCaseMapper.updateByPrimaryKey(convert) != 1) {
            throw new CommonException("error.testStepCase.update");
        }
		return ConvertHelper.convert(testCycleCaseMapper.selectByPrimaryKey(convert.getExecuteId()), TestCycleCaseE.class);
    }

    @Override
    public Page<TestCycleCaseE> query(TestCycleCaseE testCycleCaseE, PageRequest pageRequest) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
//		if (convert.getAssignedTo() != null && convert.getAssignedTo().longValue() == 0) {
//			convert.setAssignedTo(null);
//		}
        Page<TestCycleCaseAttachmentRelDO> serviceDOPage = PageHelper.doPageAndSort(pageRequest,
                () -> testCycleCaseMapper.select(convert));

        return ConvertPageHelper.convertPage(serviceDOPage, TestCycleCaseE.class);
    }

    @Override
    public List<TestCycleCaseE> query(TestCycleCaseE testCycleCaseE) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
//		if (convert.getAssignedTo() != null && convert.getAssignedTo().longValue() == 0) {
//			convert.setAssignedTo(null);
//		}
        return ConvertHelper.convertList(testCycleCaseMapper.select(convert), TestCycleCaseE.class);
    }

    @Override
    public TestCycleCaseE queryOne(TestCycleCaseE testCycleCaseE) {
        TestCycleCaseDO convert = ConvertHelper.convert(testCycleCaseE, TestCycleCaseDO.class);
//		if (convert.getAssignedTo() != null && convert.getAssignedTo().longValue() == 0) {
//			convert.setAssignedTo(null);
//		}
        List<TestCycleCaseDO> list = testCycleCaseMapper.query(convert);
        if (list.size() != 1) {
			throw new CommonException("error.cycle.case.query.not.found");
        }
        return ConvertHelper.convert(list.get(0), TestCycleCaseE.class);
    }

	@Override
	public List<TestCycleCaseE> filter(Map map) {
		return ConvertHelper.convertList(testCycleCaseMapper.filter(map), TestCycleCaseE.class);
	}

	@Override
	public List<TestCycleCaseE> queryByIssue(Long issueId) {

		return ConvertHelper.convertList(testCycleCaseMapper.queryByIssue(issueId), TestCycleCaseE.class);

	}

	@Override
	public List<TestCycleCaseE> queryCycleCaseForReporter(Long[] issueIds) {
		return ConvertHelper.convertList(testCycleCaseMapper.queryCycleCaseForReporter(issueIds), TestCycleCaseE.class);

	}

	@Override
	public Long countCaseNotRun(Long[] cycleIds) {
		return testCycleCaseMapper.countCaseNotRun(cycleIds);
	}

	@Override
	public Long countCaseNotPlain(Long[] cycleIds) {
		return testCycleCaseMapper.countCaseNotPlain(cycleIds);

	}

	@Override
	public Long countCaseSum(Long[] cycleIds) {
		return testCycleCaseMapper.countCaseSum(cycleIds);

	}

	@Override
	public void validateCycleCaseInCycle(TestCycleCaseDO testCycleCase) {
		if (testCycleCaseMapper.validateCycleCaseInCycle(testCycleCase).size() > 0) {
			throw new CommonException("error.cycle.case.insert.have.one.case.in.cycle");
		}
	}

	@Override
	public String getLastedRank(Long cycleId) {
		return testCycleCaseMapper.getLastedRank(cycleId);
	}

}
