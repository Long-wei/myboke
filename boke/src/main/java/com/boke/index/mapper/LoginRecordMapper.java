package com.boke.index.mapper;

import com.boke.index.model.LoginRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginRecordMapper {
    @Insert("insert into login_record (ip,login_time,username,user_id,user_password,login_status)values (#{ip},#{loginTime},#{username},#{userId},#{userPassword},#{loginStatus})")
    public void createLoginUserRecord(LoginRecord loginRecord);
    @Select("select * from login_record")
    public List<LoginRecord> getLoginRecord();
}
