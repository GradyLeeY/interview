package com.cl.interview.po;

import com.cl.interview.common.IdGenerator;
import com.cl.interview.common.ToObject;
import com.cl.interview.dto.BookDto;
import com.cl.interview.entity.BookEntity;
import com.cl.interview.util.ClassUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookPo implements Serializable ,ToObject<BookEntity> {

    public BookPo() {
    }

    private String id;
    private String name;
    private String type;
    private String userId;
    private Date createTime;

    @Override
    public BookEntity toObject() {
        if (this.id == null) {
            this.setId(IdGenerator.nextId());
        }
        return (BookEntity) new ClassUtils().inheritValue(new BookEntity(), this,null);

    }
}