package com.crm.platform.mapper.pub;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface PricesMapper {

    List<JSONObject> selectPricesForList();

    void insertRelation(@Param("symbol") String symbol, @Param("currency_id") Integer currencyId);

    int countBySymbol(@Param("symbol") String symbol);

    void updCurrencyBySymbol(@Param("symbol") String string, @Param("currency_id") Integer currencyId);

}
