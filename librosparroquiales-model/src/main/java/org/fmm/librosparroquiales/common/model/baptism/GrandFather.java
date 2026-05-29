package org.fmm.librosparroquiales.common.model.baptism;

import org.fmm.librosparroquiales.common.model.Relative;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("GRANDFATHER")
@Entity
public class GrandFather extends Relative {
}
