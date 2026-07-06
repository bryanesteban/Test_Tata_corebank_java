package ec.com.corebank.banquito.ErrorManagment;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;

	public CustomException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
}