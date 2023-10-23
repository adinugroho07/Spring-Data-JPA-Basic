package com.adi.belajarjpa.Listener;

import java.time.LocalDateTime;
import java.util.Calendar;

public interface UpdatedAtAware {

    //merupakan method set untuk attribute entity updatedAt. namanya harus sama dengan method set nya.
    //jadi di interface ini kita mendefine kolom mana yang akan kita update jika event listener di trigger.
    void setUpdatedAt(LocalDateTime updatedAt);

    void setCreatedAt(Calendar createdAt);
}
