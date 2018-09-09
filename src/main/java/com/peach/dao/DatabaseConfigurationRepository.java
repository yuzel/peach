package com.peach.dao;

import com.peach.model.DatabaseConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 刘宇泽
 * @date 2018/9/8
 */
public interface DatabaseConfigurationRepository extends JpaRepository<DatabaseConfiguration,Integer> {
}
