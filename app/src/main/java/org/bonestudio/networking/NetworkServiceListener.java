package org.bonestudio.networking;

public interface NetworkServiceListener
{
    interface ListResponseReceiver
    {
        void onListResponseReceived(ListResponse response);
    }
    interface DescriptionResponseReceiver
    {
        void onDescriptionResponseReceived(DetailsResponse response);
    }
    void onError(Resp response);
    void onDisconnected();
}
