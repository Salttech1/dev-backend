package kraheja.feign.confg;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import okhttp3.OkHttpClient;

@Configuration
public class FeignClientConfig {
	
//	 @Bean
//	    public OkHttpClient client() {
//	        return new OkHttpClient();
//	    }
//	 
//		@Bean
//		public HttpMessageConverters initHttpMessageConverters() {
//			return new HttpMessageConverters();
//		}
//
////		@Bean
////		public FeignErrorDecoder errorDecoder() {
////			return new FeignErrorDecoder();
////		}
//
//		@Bean
//		@Primary
//		public Encoder feignFormEncoder() {
//			ObjectFactory<HttpMessageConverters> messageConverters = this::initHttpMessageConverters;
//			return new FormEncoder(new SpringEncoder(messageConverters));
//		}

}
