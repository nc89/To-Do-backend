package com.example.todobackend.entity;

import com.example.todobackend.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.todobackend.entity.EntityId;
import java.util.List;

@Document(collection = "users")
@Getter @Setter
public class UserEntity extends EntityId{

    private String username;
    private String email;
    private String password;
    List<RoleEnum> roles;
    public UserEntity() {
    }

    public UserEntity(Long id, String username, String email, String password, List<RoleEnum> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

}
