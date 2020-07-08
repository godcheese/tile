package com.godcheese.tile.mybatis;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>  实体 entity
 * @param <ID> id
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 * <p>
 * MyBatis CRUD Mapper 接口，对应 Mapper 的 SQL 语句还需要自己手工写，使用起来更灵活
 */
public interface CrudMapper<T, ID extends Serializable> {

    /**
     * 分页所有
     *
     * @param pageable
     * @return
     */
//    Page<T> pageAll(Pageable pageable);

    /**
     * 更新单条记录
     *
     * @param entity
     * @return
     */
    int updateOne(T entity);

    /**
     * 插入单条记录
     *
     * @param entity
     * @return
     */
    int insertOne(T entity);

    /**
     * 插入所有
     *
     * @param entities
     * @return
     */
    int insertAll(List<T> entities);

    /**
     * 获取单条记录
     *
     * @param id
     * @return
     */
    T getOne(ID id);

    /**
     * 根据 id ，判断是否存在某条记录
     *
     * @param id
     * @return
     */
    boolean exists(ID id);

    /**
     * 获取所有
     *
     * @return
     */
    List<T> listAll();

    /**
     * 计数所有
     *
     * @return
     */
    int countAll();

    /**
     * 删除单条记录
     *
     * @param id
     * @return
     */
    int deleteOne(ID id);

    /**
     * 删除单条记录
     *
     * @param entity
     * @return
     */
    int deleteOne(T entity);

    /**
     * 删除所有
     *
     * @param ids
     * @return
     */
    int deleteAll(List<ID> ids);

    /**
     * 清空表
     */
    void truncate();
}
