/*
 * Copyright (c) 2019. website http://ziyuele.com
 * any questions you can  send mail 2429362606@qq.com
 */

package com.ziyue.fileserver.database.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ziyue.fileserver.database.dao.DataSource;

@Component
@Transactional
public interface DataSourceRepo extends JpaRepository<DataSource, Integer> {

    @Override
    <S extends DataSource> S save(S s);

    void deleteById(int id);

    DataSource getById(int id);
}
