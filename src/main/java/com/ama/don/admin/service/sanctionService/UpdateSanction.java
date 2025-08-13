package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.SanctionsIDao;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UpdateSanction {

    private final SanctionsIDao sanctionsIDao;

    public UpdateSanction(SanctionsIDao sanctionsIDao) {
        this.sanctionsIDao = sanctionsIDao;
    }

    public void execute(Model model) {

    }
}
