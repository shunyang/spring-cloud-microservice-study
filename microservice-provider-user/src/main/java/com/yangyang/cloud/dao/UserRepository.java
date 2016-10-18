package com.yangyang.cloud.dao;

import com.yangyang.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by chenshunyang on 2016/10/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
