package br.com.zup.bancodigital.api.helper;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

	public static void addUriInResponseHeader(Object resourceId) {
		
		var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("{id}")
				.buildAndExpand(resourceId).toUri();
			
			HttpServletResponse response = ((ServletRequestAttributes)
					RequestContextHolder.currentRequestAttributes()).getResponse();
			
			response.addHeader(HttpHeaders.LOCATION, uri.toString());		
	}
	
	public static void addUriInResponseHeader(String path) {
		var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build();
		
		HttpServletResponse response = ((ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes()).getResponse();
		
		response.addHeader(HttpHeaders.LOCATION, uri.toString());			
	}
	
	public static String createUri(String path) {
		var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build();
		return uri.toUriString();
	}
	
}
