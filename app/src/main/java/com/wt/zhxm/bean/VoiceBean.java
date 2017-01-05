package com.wt.zhxm.bean;

import java.util.ArrayList;

public class VoiceBean {
	public ArrayList<Ws> ws;

	public class Cw {
		public String w;
	}

	public class Ws {
		public ArrayList<Cw> cw;
	}

}
