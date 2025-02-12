package com.tdtu.logistics_shipments_service.dto.request;

import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import lombok.Data;

@Data
public class UpdateSegmentStatusRequest {
	Long segmentId;
	SegmentStatus segmentStatus;
}
