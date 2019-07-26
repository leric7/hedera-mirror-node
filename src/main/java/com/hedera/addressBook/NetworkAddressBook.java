package com.hedera.addressBook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.hedera.configLoader.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.HederaNetworkException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.file.FileId;

import io.github.cdimascio.dotenv.Dotenv;

import com.hedera.hashgraph.sdk.file.FileContentsQuery;

import java.io.FileOutputStream;

/**
 * This is a utility file to read back service record file generated by Hedera node
 */
public class NetworkAddressBook {

	private static final Logger log = LogManager.getLogger("recordStream-log");
	static final Marker LOGM_EXCEPTION = MarkerManager.getMarker("EXCEPTION");

    private static ConfigLoader configLoader = new ConfigLoader("./config/config.json");

	static Client client;
	static Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    
    public static void main(String[] args) {

		String addressBookFile = configLoader.getAddressBookFile();

        var client = createHederaClient();

		log.info("Fecthing New address book from node {}.", dotenv.get("NODE_ADDRESS"));
        
        try {
            var contents = new FileContentsQuery(client)
                    .setFileId(new FileId(0, 0, 102))
                    .execute();

            FileOutputStream fos = new FileOutputStream(addressBookFile);
            fos.write(contents.getFileContents().getContents().toByteArray());
            fos.close();
        } catch (FileNotFoundException e) {
    		log.error(LOGM_EXCEPTION, "Address book file {} not found.", addressBookFile);
        } catch (IOException e) {
    		log.error(LOGM_EXCEPTION, "An error occurred fetching the address book file: {} ", e.getMessage());
        } catch (HederaNetworkException e) {
    		log.error(LOGM_EXCEPTION, "An error occurred fetching the address book file: {} ", e.getMessage());
		} catch (HederaException e) {
    		log.error(LOGM_EXCEPTION, "An error occurred fetching the address book file: {} ", e.getMessage());
		}
		log.info("New address book successfully saved to {}.", addressBookFile);
	}

	private static Client createHederaClient() {
	    // To connect to a network with more nodes, add additional entries to the network map
	    var nodeAddress = dotenv.get("NODE_ADDRESS");
	    var client = new Client(Map.of(getNodeId(), nodeAddress));
	
	    // Defaults the operator account ID and key such that all generated transactions will be paid for
	    // by this account and be signed by this key
	    client.setOperator(getOperatorId(), getOperatorKey());
	
	    return client;
	}
	
    public static AccountId getNodeId() {
        return AccountId.fromString(dotenv.get("NODE_ID"));
    }

    public static AccountId getOperatorId() {
        return AccountId.fromString(dotenv.get("OPERATOR_ID"));
    }
	
    public static Ed25519PrivateKey getOperatorKey() {
        return Ed25519PrivateKey.fromString(dotenv.get("OPERATOR_KEY"));
    }
	
}


