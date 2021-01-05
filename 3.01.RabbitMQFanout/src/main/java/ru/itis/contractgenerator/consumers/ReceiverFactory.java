package ru.itis.contractgenerator.consumers;

public interface ReceiverFactory {
    Receiver getReceiver(String header);
}
