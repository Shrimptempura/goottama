package com.ama.don.admin.service.sanctionService;

import com.ama.don.admin.dao.SanctionsIDao;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class DeleteSanction {

    private final SanctionsIDao sanctionsIDao;

    public DeleteSanction(SanctionsIDao sanctionsIDao) {
        this.sanctionsIDao = sanctionsIDao;
    }

    public boolean execute(Model model) {
        boolean result = false;

        return result;
    }
}
