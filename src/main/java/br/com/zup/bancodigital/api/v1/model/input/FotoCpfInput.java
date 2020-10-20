package br.com.zup.bancodigital.api.v1.model.input;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.com.zup.bancodigital.core.validation.FileContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoCpfInput {

	@NotNull
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;	
	
}
