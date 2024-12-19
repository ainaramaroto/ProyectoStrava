package es.deusto.sd.auctions.external;

import es.deusto.sd.auctions.entity.TipoRegistro;

public class ServiceGatewayFactory {
	
	private static ServiceGatewayFactory instance = new ServiceGatewayFactory();
	
	public static ServiceGatewayFactory getInstance() {
		return instance;
	}

	private ServiceGatewayFactory() {
		
	}

	
	 public AutorizacionGateway createGateway(String provider) {
	        switch (provider.toLowerCase()) {
	            case "meta":
	                return new MetaGateway();
	            case "google":
	                return new AutorizacionGoogleGateway();
	            default:
	                throw new IllegalArgumentException("Unsupported provider: " + provider);
	        }
	    }
	
	
	
	
//    public AutorizacionGateway createGateway(TipoRegistro tr) {
//        if (tr.equals(TipoRegistro.META)){
//                return new MetaGateway();
//        }else if(tr.equals(TipoRegistro.GOOGLE)){
//                return new AutorizacionGoogleGateway();
//        }else {
//                throw new IllegalArgumentException("Unsupported provider: " + tr);
//        }
//    }
}

