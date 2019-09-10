package org.bonestudio.networking;

public interface NetworkServiceListener
{
    interface ListResponseReceiver
    {
        void onListResponseReceived(ListResponse response);
    }
    interface DetailsResponseReceiver
    {
        void onDetailsResponseReceived(DetailsResponse response);
    }
    void onError(Resp response);
    void onDisconnected();
}
