/**
 * This file was generated by the Jeddict
 */
package com.mobi5.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author stibiun
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "MobileApplicationUser.findByLogin", query = "SELECT t FROM MobileApplicationUser t WHERE t.login = :login")})
public class MobileApplicationUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic
    private String name;
    @Basic
    private String login;
    @Basic
    private String password;
    @Basic
    private String salt;
    @Basic
    private String email;
    @Basic
    private String phone;
    @ManyToMany(targetEntity = Student.class)
    private List<Student> students;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        getStudents().add(student);
        student.getMobileApplicationUsers().add(this);
    }

    public void removeStudent(Student student) {
        getStudents().remove(student);
        student.getMobileApplicationUsers().remove(this);
    }
}
