package com.crm.framework.mybatis.dao;

import java.util.List;

/**
 * 
 *
 */
public interface MybatisSqlDao {

    /**
     * 保存对象
     * 
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object save(String str, Object obj);

    /**
     * 批量保存
     * 
     * @param str
     * @param list
     * @return
     * @throws Exception
     */
    public Object batchSave(String str, List<?> list) throws Exception;

    /**
     * 修改对象
     * 
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object update(String str, Object obj);

    /**
     * 删除对象
     * 
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object delete(String str, Object obj);

    /**
     * 查找对象
     * 
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForObject(String str, Object obj);

    /**
     * 查找对象
     * 
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object queryForList(String str, Object obj);

    /**
     * 查找对象封装成Map
     * 
     * @param str
     * @param obj
     * @param key
     * @param value
     * @return
     */
    public Object queryForMap(String str, Object obj, String key, String value);

}
