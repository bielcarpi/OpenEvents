package com.grup8.OpenEvents.model.callbacks;

import com.grup8.OpenEvents.model.entities.Assistance;

public interface GetAssistancesCallback {
    void onResponse(boolean success, Assistance[] eventAssistances);
}
