package io.davorpatech.apps.musicalsurveyor.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Entity
@Table(name = "radio_listeners")
public class RadioListener {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="name", length= 25, nullable=false)
    @NotBlank
    private String name;

    @Column(name="phone", length=15)
    @NotEmpty
    @Pattern(regexp="\\+\\d+")
    private String phone;

    @Column(name="email", length= 25, nullable=false)
    @Email
      private String email;

    @Column(name="address", length=500)
    private String address;


  // constructores, getters y setters

  @Override 
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RadioListener that = (RadioListener) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id); 
  }

  @Override
  public String toString() {
    return "RadioListener{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", address='" + address + '\'' +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
