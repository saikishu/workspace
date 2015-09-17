#Program to bulk remove numbers from filenames

import os

path = r"C:\Users\vmodalav\Documents\workspace\python\prank"

def rename_files():
	files_list = os.listdir(path)
	for file_name in files_list:
		os.rename(path+"\\"+file_name, path+"\\"+file_name.translate(None, "0123456789"))
rename_files()
