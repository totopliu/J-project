package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.platform.entity.pub.GroupRebateCurrencyDTO;
import com.crm.platform.entity.pub.GroupRebateVO;

public interface GroupRebateMapper {

    void removeByGroupName(@Param("groupName") String groupName);

    void save(GroupRebateVO groupRebateVO);

    List<Integer> getLevelsByGroupName(@Param("groupName") String groupName);

    List<GroupRebateCurrencyDTO> getGroupRebateCurrency(@Param("groupName") String groupName,
            @Param("level") Integer level);

    GroupRebateVO getGroupRebateByAttr(@Param("groupName") String groupName, @Param("currencyId") Integer currencyId,
            @Param("level") Integer level);

}
