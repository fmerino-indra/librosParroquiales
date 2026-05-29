package org.fmm.librosparroquiales.common.model.baptism;

import org.fmm.librosparroquiales.common.model.Relative;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("GRANDMOTHER")
@Entity
public class GrandMother extends Relative {
}
