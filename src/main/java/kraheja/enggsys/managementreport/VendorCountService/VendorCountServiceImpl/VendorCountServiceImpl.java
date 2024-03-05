package kraheja.enggsys.managementreport.VendorCountService.VendorCountServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraheja.enggsys.managementreport.VendorCountService.VendorCountService;
import kraheja.enggsys.managementreport.bean.response.GenericResponse;
import kraheja.enggsys.repository.MatcertlnvendorhdrRepository;

@Service
public class VendorCountServiceImpl implements VendorCountService {

    @Autowired
    private MatcertlnvendorhdrRepository vendorCountRepository;

    @Override
    public GenericResponse<String> fetchVendorCountByLogicNoteNum(String logicNoteNum) {

    	int vendorCount = this.vendorCountRepository.getVendorCount(logicNoteNum);

          if (vendorCount == 0) {
              return new GenericResponse<>(false, "Vendor does not exist.");
          } else {
              return new GenericResponse<>(true, "Vendor found.");
          }
    }
}
