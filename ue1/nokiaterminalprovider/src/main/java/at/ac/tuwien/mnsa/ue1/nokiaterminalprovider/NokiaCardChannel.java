package at.ac.tuwien.mnsa.ue1.nokiaterminalprovider;

import java.nio.ByteBuffer;

import javax.smartcardio.*;

/**
 * CardChannel implementation class.
 */
@SuppressWarnings("restriction")
public class NokiaCardChannel extends CardChannel {
	private NokiaCard card;
	private int channel;

	public NokiaCardChannel(NokiaCard card, int channel) {
		this.card = card;
		this.channel = channel;
	}

	@Override
	public Card getCard() {
		return card;
	}

	@Override
	public int getChannelNumber() {
		return channel;
	}

	@Override
	public ResponseAPDU transmit(CommandAPDU capdu) throws CardException {
		return card.transmitCommand(capdu);
	}

	/**
	 * @deprecated use {@link #transmit(CommandAPDU)} instead
	 */
	@Deprecated()
	public int transmit(ByteBuffer bb, ByteBuffer bb1) throws CardException {
		ResponseAPDU response = transmit(new CommandAPDU(bb));
		byte[] binaryResponse = response.getBytes();
		bb1.put(binaryResponse);
		return binaryResponse.length;
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void close() throws CardException {

	}

}
