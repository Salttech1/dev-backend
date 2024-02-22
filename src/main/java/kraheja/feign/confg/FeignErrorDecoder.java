package kraheja.feign.confg;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        // Continue the loop if a 404 error occurs
        if (response.status() == 404) {
            // You can log the error or perform any necessary handling here
            System.out.println("404 Error occurred for method key: " + methodKey);
            return null;  // Returning null indicates that no exception should be thrown
        }
        
        // For other error codes, let Feign handle the decoding
        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
