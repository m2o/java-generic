package hr.tennis.bot.learner.utils;

import hr.tennis.bot.model.Gender;
import hr.tennis.bot.model.TournamentType;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileSet{

	public File rawFeatureTrainIn;
	public File rawFeatureTrainNames;
	public File rawFeatureTrainCoefficients;
	public File rawFeatureTrainOut;

	public File rawFeatureValidateIn;
	public File rawFeatureValidateNames;
	public File rawFeatureValidateOut;
	public File rawFeatureValidateCoefficients;

	public File rawFeatureTestAllIn;
	public File rawFeatureTestAllNames;
	public File rawFeatureTestAllOut;
	public File rawFeatureTestAllCoefficients;

	public File featureTrainIn;
	public File featureTrainNames;
	public File featureTrainOut;

	public File featureValidateIn;
	public File featureValidateNames;
	public File featureValidateOut;

	public File featureTestAllIn;
	public File featureTestAllNames;
	public File featureTestAllOut;

	public File normalize;

	public File matlabMLPw1;
	public File matlabMLPb1;
	public File matlabMLPw2;
	public File matlabMLPb2;

	public File matlabRBc;
	public File matlabRBcb;
	public File matlabRBw;
	public File matlabRBwb;

	public File classifierKNN;
	public File classifierMLP;
	public File classifierRBN;

	public static FileSet fromType(Gender gender,TournamentType type) throws IOException{

		FileSet set = new FileSet();

		String fileTemplate = String.format("data/%s/%s/raw-feature/%%s/%%s/%%s",
			        						type.toString().toLowerCase(),
			        						gender.toString().toLowerCase());

		set.rawFeatureTrainIn = new File(String.format(fileTemplate,"train","all","in"));
		set.rawFeatureTrainNames = new File(String.format(fileTemplate,"train","all","names"));
		set.rawFeatureTrainCoefficients = new File(String.format(fileTemplate,"train","all","coefficients"));
		set.rawFeatureTrainOut = new File(String.format(fileTemplate,"train","all","out"));

		set.rawFeatureValidateIn = new File(String.format(fileTemplate,"validate","all","in"));
		set.rawFeatureValidateNames = new File(String.format(fileTemplate,"validate","all","names"));
		set.rawFeatureValidateCoefficients = new File(String.format(fileTemplate,"validate","all","coefficients"));
		set.rawFeatureValidateOut = new File(String.format(fileTemplate,"validate","all","out"));

		set.rawFeatureTestAllIn = new File(String.format(fileTemplate,"test","all","in"));
		set.rawFeatureTestAllNames = new File(String.format(fileTemplate,"test","all","names"));
		set.rawFeatureTestAllCoefficients = new File(String.format(fileTemplate,"test","all","coefficients"));
		set.rawFeatureTestAllOut = new File(String.format(fileTemplate,"test","all","out"));

		fileTemplate = String.format("data/%s/%s/feature/%%s/%%s/%%s",
											type.toString().toLowerCase(),
											gender.toString().toLowerCase());

		set.featureTrainIn = new File(String.format(fileTemplate,"train","all","in"));
		set.featureTrainNames = new File(String.format(fileTemplate,"train","all","names"));
		set.featureTrainOut = new File(String.format(fileTemplate,"train","all","out"));

		set.featureValidateIn = new File(String.format(fileTemplate,"validate","all","in"));
		set.featureValidateNames = new File(String.format(fileTemplate,"validate","all","names"));
		set.featureValidateOut = new File(String.format(fileTemplate,"validate","all","out"));

		set.featureTestAllIn = new File(String.format(fileTemplate,"test","all","in"));
		set.featureTestAllNames = new File(String.format(fileTemplate,"test","all","names"));
		set.featureTestAllOut = new File(String.format(fileTemplate,"test","all","out"));

		set.normalize = new File(String.format(fileTemplate,"train","all","normalize"));

		fileTemplate = String.format("data/%s/%s/matlab/%%s",
										type.toString().toLowerCase(),
										gender.toString().toLowerCase());

		set.matlabMLPw1 = new File(String.format(fileTemplate,"mlp_w1.dat"));
		set.matlabMLPb1 = new File(String.format(fileTemplate,"mlp_b1.dat"));
		set.matlabMLPw2 = new File(String.format(fileTemplate,"mlp_w2.dat"));
		set.matlabMLPb2 = new File(String.format(fileTemplate,"mlp_b2.dat"));

		set.matlabRBc = new File(String.format(fileTemplate,"rb_centers.dat"));
		set.matlabRBcb = new File(String.format(fileTemplate,"rb_centers_biases.dat"));
		set.matlabRBw = new File(String.format(fileTemplate,"rb_weights.dat"));
		set.matlabRBwb = new File(String.format(fileTemplate,"rb_weights_biases.dat"));

		fileTemplate = String.format("data/%s/%s/classfier/classifier_%%s",
										type.toString().toLowerCase(),
										gender.toString().toLowerCase());

		set.classifierKNN = new File(String.format(fileTemplate,"KNN"));
		set.classifierMLP = new File(String.format(fileTemplate,"MLP"));
		set.classifierRBN = new File(String.format(fileTemplate,"RBN"));

		FileUtils.touch(set.rawFeatureTrainIn);
		FileUtils.touch(set.rawFeatureTrainNames);
		FileUtils.touch(set.rawFeatureTrainCoefficients);
		FileUtils.touch(set.rawFeatureTrainOut);
		FileUtils.touch(set.rawFeatureValidateIn);
		FileUtils.touch(set.rawFeatureValidateNames);
		FileUtils.touch(set.rawFeatureValidateCoefficients);
		FileUtils.touch(set.rawFeatureValidateOut);

		FileUtils.touch(set.rawFeatureTestAllIn);
		FileUtils.touch(set.rawFeatureTestAllNames);
		FileUtils.touch(set.rawFeatureTestAllCoefficients);
		FileUtils.touch(set.rawFeatureTestAllOut);

		FileUtils.touch(set.featureTrainIn);
		FileUtils.touch(set.featureTrainNames);
		FileUtils.touch(set.featureTrainOut);
		FileUtils.touch(set.featureValidateIn);
		FileUtils.touch(set.featureValidateNames);
		FileUtils.touch(set.featureValidateOut);

		FileUtils.touch(set.featureTestAllIn);
		FileUtils.touch(set.featureTestAllNames);
		FileUtils.touch(set.featureTestAllOut);

		FileUtils.touch(set.normalize);

		FileUtils.touch(set.classifierKNN);
		FileUtils.touch(set.classifierMLP);

		FileUtils.touch(set.matlabMLPw1);
		FileUtils.touch(set.matlabMLPw2);
		FileUtils.touch(set.matlabMLPb1);
		FileUtils.touch(set.matlabMLPb2);

		return set;
	}
}