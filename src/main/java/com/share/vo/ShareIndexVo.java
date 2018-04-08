package com.share.vo;

import java.util.List;

import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.shopsharebannerimages.entity.TcsbShopShareBannerImagesEntity;
import com.tcsb.shopsharecontent.entity.TcsbShopShareContentEntity;
import com.tcsb.shopsharedestailsimages.entity.TcsbShopShareDestailsImagesEntity;

public class ShareIndexVo {
	
	
	private List<TcsbShopShareBannerImagesEntity> shareBannerImages;
	
	
	private List<TcsbShopShareDestailsImagesEntity> shareDestailsImages;
	
	private List<TcsbFullcutTemplateEntity> fullcutTemplate;
	
	private TcsbShopShareContentEntity shareContent;

	public List<TcsbShopShareBannerImagesEntity> getShareBannerImages() {
		return shareBannerImages;
	}

	public void setShareBannerImages(List<TcsbShopShareBannerImagesEntity> shareBannerImages) {
		this.shareBannerImages = shareBannerImages;
	}

	public List<TcsbShopShareDestailsImagesEntity> getShareDestailsImages() {
		return shareDestailsImages;
	}

	public void setShareDestailsImages(List<TcsbShopShareDestailsImagesEntity> shareDestailsImages) {
		this.shareDestailsImages = shareDestailsImages;
	}

	public List<TcsbFullcutTemplateEntity> getFullcutTemplate() {
		return fullcutTemplate;
	}

	public void setFullcutTemplate(List<TcsbFullcutTemplateEntity> fullcutTemplate) {
		this.fullcutTemplate = fullcutTemplate;
	}

	public TcsbShopShareContentEntity getShareContent() {
		return shareContent;
	}

	public void setShareContent(TcsbShopShareContentEntity shareContent) {
		this.shareContent = shareContent;
	}
	
	

}
