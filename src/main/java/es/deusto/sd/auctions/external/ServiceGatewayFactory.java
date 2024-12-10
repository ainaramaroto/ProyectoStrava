package es.deusto.sd.auctions.external;



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
}

