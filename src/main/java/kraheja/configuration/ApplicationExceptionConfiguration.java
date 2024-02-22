package kraheja.configuration;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <p>Handle all exceptions, related to request and response processing.</p>
 * <p>Prevent stack trace or application error messages in response.</p>
 * 
 * @author sazzad.alom
 * @since 1.0.0
 */
@Log4j2
@ControllerAdvice
public class ApplicationExceptionConfiguration extends ResponseEntityExceptionHandler {
	/**
	 * Handle global exception
	 * 
	 * @param exception
	 * @return {@link GenericResponse}
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse> globalExceptionHandler(Exception exception) {
		log.error("exception occured: {}", exception.getMessage());

		return new ResponseEntity<>(GenericResponse.builder()
				.message(exception.getMessage())
				.result(Result.FAILED)
				.responseCode(ApiResponseCode.INETNAL_SERVER_ERROR)
				.build(),
				HttpStatus.OK);
	}

	/**
	 * Handle resource access exception
	 * 
	 * @param exception
	 * @return {@link GenericResponse}
	 */
	@ExceptionHandler(ResourceAccessException.class) 
	public ResponseEntity<GenericResponse> resourceAccessExceptionHandler(ResourceAccessException resourceAccessException) {
		log.error("resource access exception: {}", resourceAccessException.getMessage());

		return new ResponseEntity<>(GenericResponse.builder()
				.message(ApiResponseMessage.INETNAL_SERVER_ERROR)
				.result(Result.FAILED)
				.build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle request fields validation error
	 * 
	 * @param methodArgumentNotValidException
	 * @param headers
	 * @param status
	 * @param request
	 * @return {@link ErrorReponse}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
		log.error("method argument exception: {}", methodArgumentNotValidException.getMessage());

		StringBuilder errorMessage = new StringBuilder();

		for (FieldError error : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
			errorMessage.append(error.getField());
			errorMessage.append(" : ");
			errorMessage.append(error.getDefaultMessage());
			errorMessage.append(" ");
		}

		for (ObjectError error : methodArgumentNotValidException.getBindingResult().getGlobalErrors()) {
			errorMessage.append(error.getObjectName());
			errorMessage.append(" : ");
			errorMessage.append(error.getDefaultMessage());
			errorMessage.append(" ");
		}

		return handleExceptionInternal(methodArgumentNotValidException, 
				GenericResponse.builder()
				.result(Result.FAILED)
				.message(errorMessage.toString())
				.responseCode(ApiResponseCode.FAILED)
				.build(),
				httpHeaders, 
				HttpStatus.BAD_REQUEST, 
				webRequest);
	}

	/**
	 * Handle unsupported media type exception
	 * 
	 * @param httpMediaTypeNotSupportedException
	 * @param headers 
	 * @param status
	 * @param request
	 * @return {@link ErrorReponse}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, 
			HttpHeaders httpHeaders, 
			HttpStatus httpStatus, 
			WebRequest webRequest) {

		log.error("media type not allowed exception: {}", httpMediaTypeNotSupportedException.getMessage());

		return handleExceptionInternal(httpMediaTypeNotSupportedException,
				GenericResponse.builder()
				.result(Result.FAILED)
				.message(ApiResponseMessage.UN_SUPPORTED_MEDIA_TYPE)
				.build(),
				httpHeaders,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				webRequest);
	}

	/**
	 * Handle HTTP body processing exception
	 * 
	 * @param httpMessageNotReadableException
	 * @param httpHeaders
	 * @param httpStatus
	 * @param webRequest
	 * @return {@link Object}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException httpMessageNotReadableException, 
			HttpHeaders httpHeaders, 
			HttpStatus httpStatus, 
			WebRequest webRequest) {

		log.error("message not readable exception: {}", httpMessageNotReadableException.getMessage());

		StringBuilder errorMessage = new StringBuilder();		
		final Throwable cause = httpMessageNotReadableException.getCause();

		if (cause instanceof JsonParseException) {
			errorMessage.append(ApiResponseMessage.JSON_PARSE_ERROR);
		} else if (cause instanceof JsonMappingException) {
			errorMessage.append(ApiResponseMessage.JSON_MAPPING_ERROR);
		} else {
			errorMessage.append(ApiResponseMessage.MESSAGE_NOT_REDABLE);
		}

		return handleExceptionInternal(httpMessageNotReadableException,
				GenericResponse.builder()
				.result(Result.FAILED)
				.message(errorMessage.toString())
				.responseCode(ApiResponseCode.JSON_MAPPING_ERROR)
				.build(),
				httpHeaders,
				HttpStatus.BAD_REQUEST,
				webRequest);

	}

	/**
	 * Handle method not supported exception
	 * 
	 * @param methodNotSupportedException
	 * @param headers
	 * @param status
	 * @param request
	 * @return {@link ErrorReponse}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException methodNotSupportedException, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {

		log.error("method not supported exception: {}", methodNotSupportedException.getMessage());

		return handleExceptionInternal(methodNotSupportedException,
				GenericResponse.builder()
				.result(Result.FAILED)
				.message(ApiResponseMessage.METHOD_NOT_ALLLOWED)
				.build(), 
				headers, 
				HttpStatus.METHOD_NOT_ALLOWED,
				request);
	}
	
	/**
	 * Handle constraint violation exception
	 * 
	 * @param exception
	 * @return {@link GenericResponse}
	 */
	@ExceptionHandler(ConstraintViolationException.class) 
	public ResponseEntity<GenericResponse> constraintViolationExceptionHandler(ConstraintViolationException cve) {
		log.error("constraint violation exception handler: {}", cve.getMessage());

		StringBuilder sb = new StringBuilder();

	    for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
	    	sb.append(violation.getMessage());
	    }

		return new ResponseEntity<>(GenericResponse.builder().message(sb.toString()).result(Result.FAILED).responseCode("400").build(), 
				HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(kraheja.exception.ConstraintViolationException.class)
	    public ResponseEntity<GenericResponse> handleUniqueConstraintViolationException(kraheja.exception.ConstraintViolationException ex) {
		 return new ResponseEntity<>(GenericResponse.builder().message(ex.getMessage()).result(Result.FAILED).responseCode(ApiResponseCode.CHEQUE_NUMBER_USED).build(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	 @ExceptionHandler(kraheja.exception.InternalServerError.class)
	    public ResponseEntity<GenericResponse> handleInternalServerException(kraheja.exception.InternalServerError ex) {
		 return new ResponseEntity<>(GenericResponse.builder().message(ex.getMessage()).result(Result.FAILED).responseCode(ApiResponseCode.CHEQUE_NUMBER_USED).build(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}