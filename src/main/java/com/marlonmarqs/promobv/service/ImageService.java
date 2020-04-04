package com.marlonmarqs.promobv.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marlonmarqs.promobv.service.exceptions.FileException;

@Service
public class ImageService {

	// converte imagem em tipo jpg
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()); // extrai a extensão
		if(!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream()); // lendo a imagem do arquivo
			if("png".equals(ext)) {
				img = pngToJpg(img); // converte para jpg
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}

	}

	// codigo especifico
	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	// retorna objeto que encapsula leitura a partir de um bufferedImage, codigo especifico
	public InputStream getInputStream (BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch(IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

}