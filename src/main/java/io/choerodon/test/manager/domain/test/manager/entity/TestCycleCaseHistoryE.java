package io.choerodon.test.manager.domain.test.manager.entity;

import io.choerodon.test.manager.domain.repository.TestCycleCaseHistoryRepository;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 842767365@qq.com on 6/11/18.
 */
@Component
@Scope("prototype")
public class TestCycleCaseHistoryE {

    private Long executeId;
    private String oldValue;
    private String newValue;
    private Long objectVersionNumber;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private String field;

    @Autowired
    private TestCycleCaseHistoryRepository testCycleCaseHistoryRepository;

    public Page<TestCycleCaseHistoryE> querySelf(PageRequest pageRequest) {
        return testCycleCaseHistoryRepository.query(this, pageRequest);
    }

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

    public TestCycleCaseHistoryE addSelf() {
        return testCycleCaseHistoryRepository.insert(this);
    }

    public TestCycleCaseHistoryE updateSelf() {
        return testCycleCaseHistoryRepository.update(this);
    }

    public void deleteSelf() {
        testCycleCaseHistoryRepository.delete(this);
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
