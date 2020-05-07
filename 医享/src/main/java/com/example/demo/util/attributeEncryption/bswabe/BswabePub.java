package com.example.demo.util.attributeEncryption.bswabe;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.Data;

@Data
public class BswabePub{
	/*
	 * A public key
	 */
	public String pairingDesc;
	public Pairing p;				
	public Element g;				/* G_1 */
	public Element h;				/* G_1 */
	public Element f;				/* G_1 */
	public Element gp;			/* G_2 */
	public Element g_hat_alpha;	/* G_T */
}
