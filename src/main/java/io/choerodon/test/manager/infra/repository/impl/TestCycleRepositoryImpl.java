package io.choerodon.test.manager.infra.repository.impl;

import io.choerodon.test.manager.domain.test.manager.entity.TestCycleE;
import io.choerodon.test.manager.domain.repository.TestCycleRepository;
import io.choerodon.test.manager.infra.dataobject.TestCycleDO;
import io.choerodon.test.manager.infra.mapper.TestCycleMapper;
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
public class TestCycleRepositoryImpl implements TestCycleRepository {
    @Autowired
    TestCycleMapper cycleMapper;

    @Override
    public TestCycleE insert(TestCycleE testCycleE) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);
        if (cycleMapper.insert(convert) != 1) {
            throw new CommonException("error.testStepCase.insert");
        }
        return ConvertHelper.convert(convert, TestCycleE.class);
    }

    @Override
    public void delete(TestCycleE testCycleE) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);
        cycleMapper.delete(convert);
    }

    @Override
    public TestCycleE update(TestCycleE testCycleE) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);
        if (cycleMapper.updateByPrimaryKey(convert) != 1) {
            throw new CommonException("error.testStepCase.update");
        }
		return ConvertHelper.convert(cycleMapper.selectByPrimaryKey(convert.getCycleId()), TestCycleE.class);
    }

    @Override
    public Page<TestCycleE> query(TestCycleE testCycleE, PageRequest pageRequest) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);

        Page<TestCycleDO> serviceDOPage = PageHelper.doPageAndSort(pageRequest,
                () -> cycleMapper.select(convert));

        return ConvertPageHelper.convertPage(serviceDOPage, TestCycleE.class);
    }

    @Override
    public List<TestCycleE> query(TestCycleE testCycleE) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);
        return ConvertHelper.convertList(cycleMapper.select(convert), TestCycleE.class);

    }

    @Override
    public TestCycleE queryOne(TestCycleE testCycleE) {
        TestCycleDO convert = ConvertHelper.convert(testCycleE, TestCycleDO.class);
        return ConvertHelper.convert(cycleMapper.selectOne(convert), TestCycleE.class);

    }

    @Override
	public List<TestCycleE> queryBar(Long[] versionId) {
        return ConvertHelper.convertList(cycleMapper.query(versionId), TestCycleE.class);

    }

	@Override
	public List<TestCycleE> filter(Map parameters) {
		return ConvertHelper.convertList(cycleMapper.filter(parameters), TestCycleE.class);

	}

    @Override
	public List<TestCycleE> getCyclesByVersionId(Long versionId) {
		return ConvertHelper.convertList(cycleMapper.getCyclesByVersionId(versionId), TestCycleE.class);
	}

    @Override
    public List<Long> selectCyclesInVersions(Long[] versionIds) {
        return cycleMapper.selectCyclesInVersions(versionIds);
    }


}
