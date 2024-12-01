package com.tdtu.logistics_goods_service.service;

import com.tdtu.logistics_goods_service.viewmodel.NoFileMediaVm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface MediaService {
	NoFileMediaVm saveFile(MultipartFile multipartFile, String caption, String fileNameOverride);

	NoFileMediaVm getMedia(Long id);

	void removeMedia(Long id);
}
