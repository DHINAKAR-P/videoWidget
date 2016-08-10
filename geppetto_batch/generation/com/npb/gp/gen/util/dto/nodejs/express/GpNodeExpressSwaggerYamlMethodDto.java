package com.npb.gp.gen.util.dto.nodejs.express;

import java.util.ArrayList;
import java.util.List;

public class GpNodeExpressSwaggerYamlMethodDto {
	public String http_method;
	public String description;
	public String parameter_key;
	public List<GpNodeExpressSwaggerYamlParametersDto> parameters = new ArrayList<>();
	public List<GpNodeExpressSwaggerYamlResponseDto> responses = new ArrayList<>();
}
