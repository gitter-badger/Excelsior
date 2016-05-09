package fr.vavelinkevin.excelsior.Webservices;

import javax.xml.soap.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SOAPServices {

  private List<String[]> parameter  = new ArrayList<String[]>();
  private static SOAPMessage message;
  private static SOAPMessage response;
  public String result;
  public String request;

  public void sendSoap(String location, String nameService) throws Exception {
    SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
    SOAPConnection connection = factory.createConnection();
    try {
      response = connection.call(createMessage(location, nameService, parameter), location);
      isSoapError();
      OutputStream outputStream = new OutputStream() {
        private StringBuilder string = new StringBuilder();
        @Override
        public void write(int b) throws IOException {
          this.string.append((char) b);
          result = this.string.toString();
        }
      };
      response.writeTo(outputStream);
      System.out.println("Response SOAP Message :");
      System.out.println(result);
      connection.close();
    } catch(SOAPException exception) {
      System.out.println(exception.getMessage());
    }
  }

  private SOAPMessage createMessage(String url, String nameService, List<String[]> parameters) throws Exception {
    // Create a new instance for message SOAP
    MessageFactory messageFactory = MessageFactory.newInstance();
    // Create the message structure
    message = messageFactory.createMessage();
    // Get the part for SOAP
    SOAPPart soapPart = message.getSOAPPart();

    // Create the SOAP Envelope
    SOAPEnvelope envelope = soapPart.getEnvelope();
    // Adding a namespace inside the envelope
    envelope.addNamespaceDeclaration("afk0", url);


    // Start the body of the message
    SOAPBody soapBody = envelope.getBody();
    // All element inside soap body in AFKL Services start with afk0:NameService
    SOAPElement soapBodyElem = soapBody.addChildElement(nameService + "RequestElement", "afk0");
    String currentParameter = null;
    // Add the parameter dynamically
    SOAPElement parameterSoap = null;
    SOAPElement parameterIn;
    for(String[] parameter : parameters) {
      if(currentParameter != null || currentParameter == parameter[1]) {
        if(currentParameter == parameter[1]) {
          // If there is no location for our parameter
          if(parameter[1].equals("")) {
            parameterSoap = soapBodyElem.addChildElement(parameter[0]);
            parameterSoap.addTextNode(parameter[2]);
          } else {
            parameterIn = parameterSoap.addChildElement(parameter[0]);
            parameterIn.addTextNode(parameter[2]);
          }
        }
      }  else {
        // If there is no location for our parameter
        if(parameter[1].equals("")) {
          parameterSoap = soapBodyElem.addChildElement(parameter[0]);
          parameterSoap.addTextNode(parameter[2]);
        } else {
          parameterSoap = soapBodyElem.addChildElement(parameter[1]);
          parameterIn = parameterSoap.addChildElement(parameter[0]);
          parameterIn.addTextNode(parameter[2]);
        }
      }
      currentParameter = parameter[1];
    }

    // Get the Mime header for SOAP
    MimeHeaders headers = message.getMimeHeaders();
    // Add the action for SOAP
    headers.setHeader("SOAPAction", "http://www.af-klm.com/services/cargo/SearchCargoMessages-v1/searchCargoMessages");

    message.saveChanges();

    // LOG soap message sent
    System.out.println("Request SOAP Message = ");
    OutputStream outputStream = new OutputStream() {
      private StringBuilder string = new StringBuilder();
      @Override
      public void write(int b) throws IOException {
        this.string.append((char) b );
        request = this.string.toString();
      }
    };
    message.writeTo(outputStream);
    System.out.println(request);
    System.out.println();

    return message;
  }

  public void createParameter(String objet, String location, String valeur) {
    String[] parameters = new String[]{objet, location, valeur};
    parameter.add(parameters);
  }

  private boolean isSoapError() throws Exception {
    return response.getSOAPBody().getFault() != null;
  }
}
