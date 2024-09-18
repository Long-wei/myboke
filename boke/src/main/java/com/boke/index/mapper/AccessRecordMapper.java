package com.boke.index.mapper;

import com.boke.index.model.AccessRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccessRecordMapper {

    @Insert("insert into access_record (ip,access_time)values (#{ip},#{accessTime})")
    public int createRecord(AccessRecord accessRecord);

    @Select("select * from access_record")
    public List<AccessRecord> getAccessRecord();
}
