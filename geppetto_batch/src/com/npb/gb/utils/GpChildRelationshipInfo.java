package com.npb.gb.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;

public class GpChildRelationshipInfo {
	private GpNoun noun;
	private GpActivity activity;
	public void setNoun(GpNoun noun) {
		this.noun = noun;
	}
	public GpNoun getNoun() {
		return noun;
	}
	public GpActivity getActivity() {
		return activity;
	}
	public void setActivity(GpActivity activity) {
		this.activity = activity;
	}
}
