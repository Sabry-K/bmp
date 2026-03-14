package com.vcs.bmp.microservices.order.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "BMP_ORDER")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomJpaOrder exten {


}
