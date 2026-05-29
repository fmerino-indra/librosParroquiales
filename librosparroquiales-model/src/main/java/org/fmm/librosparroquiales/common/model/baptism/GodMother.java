package org.fmm.librosparroquiales.common.model.baptism;

import org.fmm.librosparroquiales.common.model.Relative;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Deprecated
@DiscriminatorValue("GODMOTHER")
@Entity
public class GodMother extends Relative {
}
