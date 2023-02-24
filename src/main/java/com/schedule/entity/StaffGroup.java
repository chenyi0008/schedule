package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import lombok.Data;
import org.apache.ibatis.annotations.MapKey;

@Data
public class StaffGroup {

    private Long staffId;

    private Long groupId;

}
