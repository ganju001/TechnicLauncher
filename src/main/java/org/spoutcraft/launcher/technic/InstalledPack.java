/*
 * This file is part of Technic Launcher.
 *
 * Copyright (c) 2013-2013, Technic <http://www.technicpack.net/>
 * Technic Launcher is licensed under the Spout License Version 1.
 *
 * Technic Launcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Technic Launcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package org.spoutcraft.launcher.technic;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.spoutcraft.launcher.Settings;
import org.spoutcraft.launcher.exceptions.RestfulAPIException;
import org.spoutcraft.launcher.technic.skin.ModpackOptions;
import org.spoutcraft.launcher.util.Utils;
import org.spoutcraft.launcher.util.FileUtils;

public class InstalledPack {
	private final Image image;
	private final ImageIcon background;
	private final Image icon;
	private final ModpackInfo info;
	private File installedDirectory;
	private File binDir;
	private File cacheDir;
	private File configDir;
	private File savesDir;
	private File tempDir;
	private File resourceDir;
	private File coremodsDir;
	
	public InstalledPack(ModpackInfo info) throws IOException {
		this(info, info.getIcon(), info.getImg(), new ImageIcon(info.getBackground().getScaledInstance(880, 520, Image.SCALE_SMOOTH)));
	}

	public InstalledPack(ModpackInfo info, Image icon, Image image, ImageIcon background) {
		this.icon = icon;
		this.info = info;
		this.image = image;
		this.background = background;
		
		String location = Settings.getPackDirectory(getName());
		
		if (location == null) {
			installedDirectory = new File(Utils.getLauncherDirectory(), getName());
		} else {
			installedDirectory = new File(location);
		}
		
		initDirectories();
	}
	public ImageIcon getBackground() {
		return background;
	}

	public Image getIcon() {
		return icon;
	}

	public ImageIcon getImage(int width, int height) {
		return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	public String getName() {
		return info.getName();
	}

	public String getDisplayName() {
		return info.getDisplayName();
	}

	public ModpackInfo getInfo() {
		return info;
	}
	
	public String getBuild() {
		String build = Settings.getModpackBuild(getName());
		if (build.equals(ModpackOptions.LATEST)) {
			build = info.getLatest();
		} else if (build.equals(ModpackOptions.RECOMMENDED)) {
			build = info.getRecommended();
		}
		
		return build;
	}
	
	public Modpack getModpack() throws RestfulAPIException {
		return TechnicRestAPI.getModpack(info, getBuild());
	}
	
	public void initDirectories() {
		binDir = new File(installedDirectory, "bin");
		cacheDir = new File(installedDirectory, "cache");
		configDir = new File(installedDirectory, "config");
		savesDir = new File(installedDirectory, "saves");
		tempDir = new File(installedDirectory, "temp");
		resourceDir = new File(installedDirectory, "resources");
		coremodsDir = new File(installedDirectory, "coremods");
		
		binDir.mkdirs();
		cacheDir.mkdirs();
		configDir.mkdirs();
		savesDir.mkdirs();
		tempDir.mkdirs();
		resourceDir.mkdirs();
		coremodsDir.mkdirs();
	}
	
	public void setPackDirectory(File packPath) {
		FileUtils.moveDirectory(installedDirectory, packPath);
		Settings.setPackDirectory(getName(), packPath.getPath());
		installedDirectory = packPath;
		initDirectories();
	}
	
	public File getPackDirectory() {
		return installedDirectory;
	}
	
	public File getBinDir() {
		return binDir;
	}
	
	public File getCacheDir() {
		return cacheDir;
	}
	
	public File getConfigDir() {
		return configDir;
	}
	
	public File getSavesDir() {
		return savesDir;
	}
	
	public File getTempDir() {
		return tempDir;
	}
	
	public File getresourceDir() {
		return resourceDir;
	}
}