package com.netand.avocado.authorization.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationClientRequest;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationRedirectUriRequest;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationUserRequest;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationClientResponse;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationRedirectUriResponse;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class IamConnector {

	// TODO. IAM 인증 추가
	private String iamServerIp = "192.168.1.50:8443";

	private SSLContext sslContext;
	private String httpScheme = "http";

	private ObjectMapper mapper;

	public IamConnector() {

		this.mapper = new ObjectMapper();
		mapper.enable( SerializationFeature.INDENT_OUTPUT );
	}

	private CloseableHttpClient createHttpClient() {

		if ( sslContext == null ) {

			return HttpClients.createDefault();
		}

		HttpClientBuilder builder = HttpClients.custom();

//		// 인증서 오류 무시...
//		SSLContextBuilder sslContextBuilder = new SSLContextBuilder().loadTrustMaterial( TrustAllStrategy.INSTANCE );
//		SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory( sslContextBuilder.build(), NoopHostnameVerifier.INSTANCE );
//
//		builder.setSSLSocketFactory( sslConnectionFactory );

		return builder.build();
	}

	private String sendRequest( HttpRequestBase requestBase ) throws AuthorizationServerException {

		CloseableHttpClient httpClient = this.createHttpClient();

		CloseableHttpResponse httpResponse = null;
		StringBuffer response = null;

		try {

			httpResponse = httpClient.execute( requestBase );
			BufferedReader reader = new BufferedReader( new InputStreamReader( httpResponse.getEntity().getContent() ) );

			String inputLine;
			response = new StringBuffer();

			while ( ( inputLine = reader.readLine() ) != null ) {

				response.append( inputLine );
			}

			reader.close();
			httpClient.close();

		} catch ( IOException e ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.HttpCommunicationError )
					.attribute( "url", requestBase.getURI().toString() )
					.cause( e )
					.build();
		}

		log.info( "response : {}", response.toString() );

		return response.toString();
	}

	public VerificationRedirectUriResponse verifyOfClientAndRedirectUri( VerificationRedirectUriRequest request ) throws AuthorizationServerException {

//		String requestUri = IamApiPaths.clients.FindByRedirectUri
//								.replace( "{id}", request.getClientId() )
//								.replace( "{redirectUri}", UriEncoder.encode( request.getRedirectUri() ) );
//
//		HttpGet httpGet = new HttpGet( String.format( "%s://%s%s", httpScheme, iamServerIp, requestUri ) );
//
//		String response = this.sendRequest( httpGet );

		return VerificationRedirectUriResponse.builder()
				.result( true )
				.message( "SUCCESS" )
				.clientId( request.getClientId() )
				.redirectUri( request.getRedirectUri() )
				.build();
	}

	public VerificationClientResponse verifyOfClient( VerificationClientRequest request ) throws AuthorizationServerException {

//		HttpPost httpPost = new HttpPost( String.format( "%s://%s%s", httpScheme, iamServerIp, IamApiPaths.clients.Authorize ) );
//
//		try {
//
//			String req = mapper.writeValueAsString( request );
//			StringEntity input = new StringEntity( req );
//
//			input.setContentType( "application/json" );
//			httpPost.setEntity( input );
//
//		} catch ( JsonProcessingException | UnsupportedEncodingException e ) {
//
//			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.InvalidDataType )
//					.cause( e )
//					.build();
//		}
//
//		String response = this.sendRequest( httpPost );

		return VerificationClientResponse.builder()
				.result( true )
				.message( "SUCCESS" )
				.clientId( request.getClientId() )
				.build();
	}

	public VerificationUserResponse verifyOfUser( VerificationUserRequest request ) throws AuthorizationServerException {

//		HttpPost httpPost = new HttpPost( String.format( "%s://%s%s", httpScheme, iamServerIp, IamApiPaths.users.Authorize ) );
//
//		try {
//
//			String req = mapper.writeValueAsString( request );
//			StringEntity input = new StringEntity( req );
//
//			input.setContentType( "application/json" );
//			httpPost.setEntity( input );
//
//		} catch ( JsonProcessingException | UnsupportedEncodingException e ) {
//
//			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.InvalidDataType )
//					.cause( e )
//					.build();
//		}
//
//		String response = this.sendRequest( httpPost );

		return VerificationUserResponse.builder()
				.result( true )
				.message( "SUCCESS" )
				.userName( request.getUserName() )
				.build();
	}

}
