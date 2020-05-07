package com.example.demo.util.attributeEncryption.bswabe;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class BswabeMsk {
	/*
	 * A master secret key
	 */
	public Element beta; /* Z_r */
	public Element g_alpha; /* G_2 */	
}
