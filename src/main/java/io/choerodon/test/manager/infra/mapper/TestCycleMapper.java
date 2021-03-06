package io.choerodon.test.manager.infra.mapper;

import io.choerodon.test.manager.infra.dataobject.TestCycleDO;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
public interface TestCycleMapper extends BaseMapper<TestCycleDO> {

	List<TestCycleDO> query(@Param("versionIds") Long[] versionId);

	List<TestCycleDO> filter(Map maps);

	List<TestCycleDO> getCyclesByVersionId(@Param("versionId") Long versionId);

	List<Long> selectCyclesInVersions(@Param("versionIds") Long[] versionIds);

//    List<Map<String, Long>> queryAboutBar(@Param("cycleId") Long cycleId);
}
